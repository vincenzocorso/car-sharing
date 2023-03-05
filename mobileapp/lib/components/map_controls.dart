import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import './map_controls_model.dart';

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

class GPSButton extends StatelessWidget {
  const GPSButton({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
      backgroundColor: Theme.of(context).primaryColor,
      onPressed: () =>
          Provider.of<MapControlsModel>(context, listen: false).centerCamera(),
      child: Icon(Provider.of<MapControlsModel>(context).isCameraCentered
          ? Icons.gps_fixed_outlined
          : Icons.gps_not_fixed_outlined),
    );
  }
}
