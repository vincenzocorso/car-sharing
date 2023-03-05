import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:mapbox_maps_flutter/mapbox_maps_flutter.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:provider/provider.dart';

import './map_controls.dart';
import './map_controls_model.dart';

class StreetMap extends StatefulWidget {
  const StreetMap({Key? key}) : super(key: key);

  @override
  State<StreetMap> createState() => _StreetMapState();
}

class _StreetMapState extends State<StreetMap> {
  MapboxMap? _mapboxMap;
  final _mapModel = MapControlsModel();

  _onMapCreated(MapboxMap mapboxMap) {
    _mapboxMap = mapboxMap;

    setupMapSettings();

    _mapModel.updateMap(mapboxMap);
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
    return ChangeNotifierProvider<MapControlsModel>(
      create: (context) => _mapModel,
      child: Stack(
        children: [
          MapWidget(
            resourceOptions: ResourceOptions(
              accessToken: dotenv.env['PUBLIC_MAPBOX_TOKEN']!,
            ),
            styleUri: MapboxStyles.LIGHT,
            cameraOptions: CameraOptions(
              center:
                  Point(coordinates: Position(9.188540, 45.464664)).toJson(),
              zoom: 14.0,
            ),
            onMapCreated: _onMapCreated,
            onScrollListener: (_) => _mapModel.onMapScroll(),
          ),
          const MapControls(),
        ],
      ),
    );
  }
}
