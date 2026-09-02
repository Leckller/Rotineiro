import 'package:flutter/material.dart';
import 'package:mobile/providers/auth_provider.dart';
import 'package:mobile/screens/app_screens.dart';
import 'package:mobile/screens/auth/login_page.dart';
import 'package:mobile/screens/auth/register_page.dart';
import 'package:mobile/screens/auth/welcome_page.dart';
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
        ChangeNotifierProvider(create: (ctx) => AuthProvider())
      ],
      child: MaterialApp(
        title: 'Flutter Demo',
        theme: ThemeData(
          colorScheme: .fromSeed(seedColor: const Color.fromARGB(255, 89, 212, 199)),
        ),
        home: WelcomePage(),
        routes: {
          Appscreens().WELCOME: (ctx) => WelcomePage(),
          Appscreens().LOGIN: (ctx) => LoginPage(),
          Appscreens().REGISTER: (ctx) => RegisterPage(),
        },
      ),
    );
  }
}
