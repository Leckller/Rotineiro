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
  String pageState = AuthStates().WELCOME;

  void setPageState(String newState) {
    setState(() {
      pageState = newState;
    });
  }

  bool _isWelcome() => pageState == AuthStates().WELCOME;
  bool _isLogin() => pageState == AuthStates().LOGIN;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _isWelcome()
          ? Welcome(setPageState: setPageState)
          : _isLogin()
          ? Login(setPageState: setPageState)
          : Register(setPageState: setPageState),
    );
  }
}

class Welcome extends StatelessWidget {
  final Function setPageState;
  const Welcome({super.key, required this.setPageState});

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
            ElevatedButton(
              onPressed: () {
                setPageState(AuthStates().LOGIN);
              },
              child: Text("Login"),
            ),
            ElevatedButton(
              onPressed: () {
                setPageState(AuthStates().REGISTER);
              },
              child: Text("Cadastro"),
            ),
          ],
        ),
      ],
    );
  }
}

class Login extends StatelessWidget {
  final Function setPageState;
  const Login({super.key, required this.setPageState});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          children: [
            IconButton(
              onPressed: () {
                setPageState(AuthStates().WELCOME);
              },
              icon: Icon(Icons.keyboard_arrow_left_sharp),
            ),
            Text("Login"),
          ],
        ),
        Form(child: Column()),
      ],
    );
  }
}

class Register extends StatelessWidget {
  final Function setPageState;
  const Register({super.key, required this.setPageState});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          children: [
            IconButton(
              onPressed: () {
                setPageState(AuthStates().WELCOME);
              },
              icon: Icon(Icons.keyboard_arrow_left_sharp),
            ),
            Text("Cadastro"),
          ],
        ),
        Form(child: Column()),
      ],
    );
  }
}
