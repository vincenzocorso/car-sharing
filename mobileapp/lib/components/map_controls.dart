import 'package:flutter/material.dart';

class MapControls extends StatelessWidget {
  const MapControls({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(
        horizontal: 10.0,
        vertical: 20.0,
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              FloatingActionButton(
                backgroundColor: Theme.of(context).primaryColor,
                shape: const RoundedRectangleBorder(
                  borderRadius: BorderRadius.all(Radius.circular(15.0)),
                ),
                onPressed: () {
                  print("Menu button pressed");
                },
                child: const Icon(Icons.menu_rounded),
              )
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: const [
              GPSButton(),
            ],
          ),
        ],
      ),
    );
  }
}

class GPSButton extends StatefulWidget {
  const GPSButton({Key? key}) : super(key: key);

  @override
  State<GPSButton> createState() => _GPSButtonState();
}

class _GPSButtonState extends State<GPSButton> {
  bool _isPositionCentered = false;

  void onGPSButtonPressed() {
    setState(() {
      _isPositionCentered = !_isPositionCentered;
    });
  }

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
      backgroundColor: Theme.of(context).primaryColor,
      onPressed: onGPSButtonPressed,
      child: Icon(_isPositionCentered
          ? Icons.gps_fixed_outlined
          : Icons.gps_not_fixed_outlined),
    );
  }
}
