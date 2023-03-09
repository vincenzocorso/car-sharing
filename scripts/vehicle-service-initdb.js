db = db.getSiblingDB("app");

db.vehicle_models.insertMany([{
    "_id": "64099a611bbf5849f102b28e",
    "name": "BMW 1 Series",
    "seats": 5,
    "transmissionType": "AUTOMATIC",
    "engineType": "FUEL",
    "rates": {
        "ratePerMinute": 0.31,
        "ratePerHour": 8.0,
        "ratePerDay": 26.67,
        "ratePerKilometer": 0.19
    }
}, {
    "_id": "64099a611bbf5849f102b28f",
    "name": "BMW 2 Series Active Tourer",
    "seats": 5,
    "transmissionType": "AUTOMATIC",
    "engineType": "FUEL",
    "rates": {
        "ratePerMinute": 0.31,
        "ratePerHour": 8.0,
        "ratePerDay": 26.67,
        "ratePerKilometer": 0.19
    }
}, {
    "_id": "64099a611bbf5849f102b290",
    "name": "BMW X1",
    "seats": 5,
    "transmissionType": "MANUAL",
    "engineType": "FUEL",
    "rates": {
        "ratePerMinute": 0.34,
        "ratePerHour": 9.09,
        "ratePerDay": 30.0,
        "ratePerKilometer": 0.19
    }
}, {
    "_id": "64099a611bbf5849f102b291",
    "name": "Citroën C3",
    "seats": 5,
    "transmissionType": "AUTOMATIC",
    "engineType": "FUEL",
    "rates": {
        "ratePerMinute": 0.19,
        "ratePerHour": 5.67,
        "ratePerDay": 21.67,
        "ratePerKilometer": 0.19
    }
}, {
    "_id": "64099a611bbf5849f102b292",
    "name": "Fiat 500e",
    "seats": 4,
    "transmissionType": "AUTOMATIC",
    "engineType": "ELECTRIC",
    "rates": {
        "ratePerMinute": 0.19,
        "ratePerHour": 5.67,
        "ratePerDay": 19.99,
        "ratePerKilometer": 0.19
    }
}, {
    "_id": "64099a611bbf5849f102b293",
    "name": "Peugeot 208",
    "seats": 5,
    "transmissionType": "AUTOMATIC",
    "engineType": "FUEL",
    "rates": {
        "ratePerMinute": 0.28,
        "ratePerHour": 6.84,
        "ratePerDay": 21.67,
        "ratePerKilometer": 0.19
    }
}])

db.vehicles.insertMany([{
    "licensePlate": "FG178RF",
    "vehicleModelId": "64099a611bbf5849f102b292",
    "position": {
        "latitude": 49.8397,
        "longitude": 24.0297
    },
    "autonomy": 85.4,
    "lastStatusUpdate": 1678351318643,
    "status": "AVAILABLE",
    "unlockCode": null
}])