import 'package:flutter/material.dart';
import 'package:mobile/core/token_storage.dart';
import 'package:mobile/providers/auth_provider.dart';
import 'package:mobile/providers/routine_provider.dart';
import 'package:mobile/screens/auth/auth_gate.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (ctx) => AuthProvider(TokenStorage())..checkAuth(),
        ),
        ChangeNotifierProvider(create: (ctx) => RoutineProvider()),
      ],
      child: MaterialApp(
        debugShowCheckedModeBanner: false,
        title: 'Flutter Demo',
        theme: ThemeData(
          colorScheme: .fromSeed(
            seedColor: const Color.fromARGB(255, 89, 212, 199),
          ),
        ),
        home: AuthGate(),
        routes: {
        },
      ),
    );
  }
}
