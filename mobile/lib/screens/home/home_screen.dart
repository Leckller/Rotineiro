import 'package:flutter/material.dart';
import 'package:mobile/components/app_navibar.dart';
import 'package:mobile/providers/routine_provider.dart';
import 'package:mobile/screens/home/components/home_data_resume.dart';
import 'package:mobile/screens/home/components/home_header.dart';
import 'package:mobile/screens/home/components/home_routines.dart';
import 'package:provider/provider.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      context.read<RoutineProvider>().findAll();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.only(right: 16, left: 16, top: 16),
        child: ListView(
          children: [
            HomeHeader(),
            SizedBox(height: 12,),
            HomeDataResume(),
            SizedBox(height: 12,),
            HomeRoutines()
          ],
        ),
      ),
      bottomNavigationBar: AppNavibar(currentRoute: ModalRoute.of(context)!.settings.name!,),
    );
  }
  
}