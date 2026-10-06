import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/core/api_client.dart';
import 'package:mobile/core/auth_interceptor.dart';
import 'package:mobile/core/token_storage.dart';
import 'package:mobile/providers/auth_provider.dart';
import 'package:mobile/providers/routine_provider.dart';
import 'package:mobile/providers/task_provider.dart';
import 'package:mobile/screens/app_screens.dart';
import 'package:mobile/screens/auth/auth_gate.dart';
import 'package:mobile/screens/library/library_screen.dart';
import 'package:mobile/screens/routine/routine_details_screen.dart';
import 'package:mobile/service/auth_service.dart';
import 'package:mobile/service/routine_service.dart';
import 'package:mobile/service/task_service.dart';
import 'package:provider/provider.dart';

void main() {
  final storage = TokenStorage();
  final dio = buildDio();
  final authService = AuthService(dio);
  final routineService = RoutineService(dio);
  final taskService = TaskService(dio);
  final authProvider = AuthProvider(storage, authService)..checkAuth();
  final routineProvider = RoutineProvider(service: routineService);
  final taskProvider = TaskProvider(service: taskService);

  dio.interceptors.add(AuthInterceptor(storage, authProvider.logout));

  runApp(MyApp(authProvider: authProvider, routineProvider: routineProvider, taskProvider: taskProvider, dio: dio));
}

class MyApp extends StatelessWidget {
  MyApp({super.key, required this.authProvider, required this.routineProvider, required this.taskProvider,  required this.dio});

  final AuthProvider authProvider;
  final RoutineProvider routineProvider;
  final TaskProvider taskProvider;
  final Dio dio;
  final routes = Appscreens();

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [  
        ChangeNotifierProvider.value(value: authProvider),
        ChangeNotifierProvider.value(value: routineProvider),
        ChangeNotifierProvider.value(value: taskProvider,)
      ],
      child: MaterialApp(
        debugShowCheckedModeBanner: false,
        title: 'Rotineiro',
        theme: ThemeData(
          colorScheme: .fromSeed(
            seedColor: const Color.fromARGB(255, 89, 212, 199),
          ),
        ),
        home: const AuthGate(),
        routes: {
          routes.library: (ctx) => LibraryScreen(),
          routes.routineDetails: (ctx) => RoutineDetailsScreen()
        },
      ),
    );
  }
}
