import 'package:flutter/foundation.dart';
import 'package:mapbox_maps_flutter/mapbox_maps_flutter.dart';

class MapModel extends ChangeNotifier {
  MapboxMap? _map;

  void updateMap(MapboxMap map) {
    _map = map;
  }

  void centerCameraPosition() async {
    // TODO: center the camera position
  }
}
