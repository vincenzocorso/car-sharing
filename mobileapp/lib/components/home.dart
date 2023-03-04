import 'package:flutter/material.dart';

import './street_map.dart';

class Home extends StatelessWidget {
  const Home({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: const Center(
        child: StreetMap(),
      ),
    );
  }
}
