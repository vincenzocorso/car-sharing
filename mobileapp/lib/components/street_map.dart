import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:mapbox_maps_flutter/mapbox_maps_flutter.dart';
import 'package:permission_handler/permission_handler.dart';

import './map_controls.dart';

class StreetMap extends StatefulWidget {
  const StreetMap({Key? key}) : super(key: key);

  @override
  State<StreetMap> createState() => _StreetMapState();
}

class _StreetMapState extends State<StreetMap> {
  MapboxMap? _mapboxMap;

  _onMapCreated(MapboxMap mapboxMap) {
    _mapboxMap = mapboxMap;

    setupMapSettings();
  }

  Future<void>? setupMapSettings() async {
    // disable the scale bar
    var scaleBarSettings = ScaleBarSettings(enabled: false);
    await _mapboxMap?.scaleBar.updateSettings(scaleBarSettings);

    // disable the compass
    var compassSettings = CompassSettings(enabled: false);
    await _mapboxMap?.compass.updateSettings(compassSettings);

    // enable the user marker
    await Permission.locationWhenInUse.request();
    var locationComponentSettings = LocationComponentSettings(enabled: true);
    await _mapboxMap?.location.updateSettings(locationComponentSettings);
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        MapWidget(
          resourceOptions: ResourceOptions(
            accessToken: dotenv.env['PUBLIC_MAPBOX_TOKEN']!,
          ),
          styleUri: MapboxStyles.LIGHT,
          cameraOptions: CameraOptions(
            center: Point(coordinates: Position(9.188540, 45.464664)).toJson(),
            zoom: 14.0,
          ),
          onMapCreated: _onMapCreated,
        ),
        const MapControls(),
      ],
    );
  }
}
