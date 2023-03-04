import 'package:flutter/material.dart';

import './home.dart';

class App extends StatelessWidget {
  const App({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Car Sharing',
      theme: ThemeData(
        primaryColor: Colors.black38,
      ),
      home: Home(),
    );
  }
}
