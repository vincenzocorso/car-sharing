document.addEventListener('DOMContentLoaded', async () => {
    // Load the publishable key from a local file.
    const publishableKeyFilePath = 'env.txt';
    const publishableKey = await fetch(publishableKeyFilePath)
        .then(response => response.text());
    if (!publishableKey) {
        console.log(`No publishable key loaded. Please check ${publishableKeyFilePath} and try again`);
        alert(`Please set your Stripe publishable API key in ${publishableKeyFilePath}`);
    } else {
        console.log(`Pub key loaded: ${publishableKey}`);
    }

    const stripe = Stripe(publishableKey);
    const elements = stripe.elements();

    // Custom styling can be passed to options when creating an Element.
    const style = {
        base: {
            color: '#32325d',
            fontSize: '16px',
            '::placeholder': {
                color: '#aab7c4'
            },
            ':-webkit-autofill': {
                color: '#32325d',
            },
        },
        invalid: {
            color: '#fa755a',
            iconColor: '#fa755a',
            ':-webkit-autofill': {
                color: '#fa755a',
            },
        },
    };

    const options = {
        style,
        supportedCountries: ['SEPA'],
        // Elements can use a placeholder as an example IBAN that reflects
        // the IBAN format of your customer's country. If you know your
        // customer's country, we recommend passing it to the Element as the
        // placeholderCountry.
        placeholderCountry: 'DE',
    };

    // Create an instance of the IBAN Element
    const iban = elements.create('iban', options);

    // Add an instance of the IBAN Element into the `iban-element` <div>
    iban.mount('#iban-element');

    const form = document.getElementById('setup-form');
    const accountholderName = document.getElementById('accountholder-name');
    const email = document.getElementById('email');
    const submitButton = document.getElementById('submit-button');

    const clientSecret = await fetch('http://localhost/paymentmethods', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            customerId: 'abc1234'
        }),
    }).then(result => result.json())
        .then(body => body['metadata']['stripe__client_secret'])

    form.addEventListener('submit', (event) => {
        event.preventDefault();
        stripe.confirmSepaDebitSetup(
            clientSecret,
            {
                payment_method: {
                    sepa_debit: iban,
                    billing_details: {
                        name: accountholderName.value,
                        email: email.value,
                    },
                },
            }
        );
    });
});
