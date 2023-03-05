import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:mapbox_maps_flutter/mapbox_maps_flutter.dart';

class MapControlsModel extends ChangeNotifier {
  MapboxMap? _map;
  bool _isCameraCentered = false;

  bool get isCameraCentered => _isCameraCentered;

  void updateMap(MapboxMap map) {
    _map = map;
  }

  void centerCamera() async {
    var position = await _map?.style.getPuckPosition();
    await _map?.flyTo(
      CameraOptions(
        center: Point(coordinates: position!).toJson(),
        zoom: 16.0,
      ),
      MapAnimationOptions(
        duration: 1000,
        startDelay: 0,
      ),
    );

    _isCameraCentered = true;
    notifyListeners();
  }

  void onMapScroll() {
    _isCameraCentered = false;
    notifyListeners();
  }
}

// FIXME: this is a temporary solution. At the time of writing there is no official way to get the user position.
// See https://github.com/mapbox/mapbox-maps-flutter/issues/33
extension PuckPosition on StyleManager {
  Future<Position> getPuckPosition() async {
    Layer? layer;
    if (Platform.isAndroid) {
      layer = await getLayer('mapbox-location-indicator-layer');
    } else {
      layer = await getLayer('puck');
    }
    final location = (layer as LocationIndicatorLayer).location;
    return Future.value(Position(location![1]!, location[0]!));
  }
}
