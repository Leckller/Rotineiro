import 'package:flutter/material.dart';
import 'package:mobile/screens/app_screens.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  final _formKey = GlobalKey<FormState>();

  void _submitForm() {
    if(_formKey.currentState!.validate()) {

      _formKey.currentState!.save();

      // Adicionar envio future p api dpois

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Submited!"), duration: Duration(seconds: 2),)
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Row(
              children: [
                IconButton(
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                  icon: Icon(Icons.keyboard_arrow_left_sharp),
                ),
                Text("Cadastro"),
              ],
            ),
            Column(
              spacing: 8,
              children: [
                Form(
                  key: _formKey,
                  child: Column(
                    spacing: 8,
                    children: [
                      TextFormField(
                        textInputAction: TextInputAction.next,
                        validator: (value) {
                          String email = value ?? "";
                          if (email.isEmpty) {
                            return "O campo deve ser preenchido.";
                          }
                          return null;
                        },
                        decoration: InputDecoration(label: Text("Email")),
                      ),
                      TextFormField(
                        textInputAction: TextInputAction.next,
                        validator: (value) {
                          String password = value ?? "";
                          if (password.isEmpty) {
                            return "O campo deve ser preenchido.";
                          }
                          return null;
                        },
                        decoration: InputDecoration(label: Text("Senha")),
                      ),
                      TextFormField(
                        validator: (value) {
                          String confirmPasswod = value ?? "";
                          if (confirmPasswod.isEmpty) {
                            return "O campo deve ser preenchido.";
                          }
                          return null;
                        },
                        decoration: InputDecoration(
                          label: Text("Confirme a senha"),
                        ),
                      ),
                      Row(
                        children: [
                          Expanded(
                            child: ElevatedButton(
                              onPressed: () {
                                _submitForm();
                              },
                              child: Padding(
                                padding: const EdgeInsets.all(8.0),
                                child: Text("Entrar"),
                              ),
                            ),
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
                Row(
                  children: [
                    Text("Já possui uma conta? faça o"),
                    TextButton(
                      onPressed: () {
                        Navigator.of(context).pushReplacementNamed(Appscreens().LOGIN);
                      },
                      child: Text("Login"),
                    ),
                  ],
                ),
              ],
            ),
            Container(),
          ],
        ),
      ),
    );
  }
}
