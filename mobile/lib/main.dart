import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:mobile/core/api_client.dart';
import 'package:mobile/core/auth_interceptor.dart';
import 'package:mobile/core/token_storage.dart';
import 'package:mobile/providers/auth_provider.dart';
import 'package:mobile/providers/routine_provider.dart';
import 'package:mobile/screens/auth/auth_gate.dart';
import 'package:mobile/service/auth_service.dart';
import 'package:mobile/service/routine_service.dart';
import 'package:provider/provider.dart';

void main() {
  final storage = TokenStorage();
  final dio = buildDio();
  final authService = AuthService(dio);
  final routineService = RoutineService(dio);
  final authProvider = AuthProvider(storage, authService)..checkAuth();
  final routineProvider = RoutineProvider(service: routineService);

  dio.interceptors.add(AuthInterceptor(storage, authProvider.logout));

  runApp(MyApp(authProvider: authProvider, routineProvider: routineProvider, dio: dio));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key, required this.authProvider, required this.routineProvider, required this.dio});

  final AuthProvider authProvider;
  final RoutineProvider routineProvider;
  final Dio dio;

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider.value(value: authProvider),
        ChangeNotifierProvider.value(value: routineProvider),
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
        routes: {},
      ),
    );
  }
}
