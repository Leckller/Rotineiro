import 'package:flutter/material.dart';
import 'package:mobile/screens/app_screens.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  late final TextEditingController emailController;
  final _keyForm = GlobalKey<FormState>();

  void _submitForm() {
    if(_keyForm.currentState!.validate()) {
      _keyForm.currentState!.save();

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Lets go"), duration: Duration(seconds: 2),)
      );
    }
  }

  @override
  void initState() {
    super.initState();

    emailController = TextEditingController();
  }

  @override
  void dispose() {
    super.dispose();

    emailController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          spacing: 16,
          children: [
            Row(
              children: [
                IconButton(
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                  icon: Icon(Icons.keyboard_arrow_left_sharp),
                ),
                Text("Login"),
              ],
            ),
            Form(
              key: _keyForm,
              child: SizedBox(
                width: double.infinity,
                child: Column(
                  spacing: 16,
                  children: [
                    TextFormField(
                      textInputAction: TextInputAction.next,
                      validator: (value) {
                        String password = value ?? "";
                        if (password.isEmpty) {
                          return "O campo deve ser preenchido.";
                        }
                        return null;
                      },
                      controller: emailController,
                      decoration: InputDecoration(label: Text("Email")),
                    ),
                    TextFormField(
                      validator: (value) {
                        String password = value ?? "";
                        if (password.isEmpty) {
                          return "O campo deve ser preenchido.";
                        }
                        return null;
                      },
                      decoration: InputDecoration(label: Text("Senha")),
                    ),
                    Row(
                      children: [
                        Expanded(
                          child: ElevatedButton(
                            onPressed: () {
                              _submitForm();
                            },
                            child: Text("Entrar"),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            Row(
              children: [
                Text("Não possui uma conta?"),
                TextButton(
                  onPressed: () {
                    Navigator.of(context).pushReplacementNamed(Appscreens().REGISTER);
                  },
                  child: Text("Cadastre-se!"),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
