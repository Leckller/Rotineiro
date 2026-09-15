import 'package:flutter/material.dart';
import 'package:mobile/screens/home/components/home_data_resume.dart';
import 'package:mobile/screens/home/components/home_header.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          HomeHeader(),
          HomeDataResume(),
          
        ],
      ),
    );
  }

}