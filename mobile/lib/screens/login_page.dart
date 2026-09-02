import 'package:flutter/material.dart';

class AuthStates {
  final WELCOME = "WELCOME";
  final LOGIN = "LOGIN";
  final REGISTER = "REGISTER";
}

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  String state = AuthStates().WELCOME;

  bool _isWelcome() => state == AuthStates().WELCOME;
  bool _isLogin() => state == AuthStates().LOGIN;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _isWelcome()
          ? Welcome()
          : _isLogin()
          ? Login()
          : Register(),
    );
  }
}

class Welcome extends StatelessWidget {
  const Welcome({super.key});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Image.asset(""),
        Column(
          children: [
            Text("Bem vindo ao Rotineiro!"),
            Text(
              "Gerencie suas tarefas e rotinas de um jeito simples e fácil!",
            ),
          ],
        ),
        Column(
          children: [
            ElevatedButton(onPressed: () {}, child: Text("Login")),
            ElevatedButton(onPressed: () {}, child: Text("Cadastro")),
          ],
        ),
      ],
    );
  }
}

class Login extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Stack(children: [Form(child: Column())]);
  }
}

class Register extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Stack(children: [Form(child: Column())]);
  }
}
