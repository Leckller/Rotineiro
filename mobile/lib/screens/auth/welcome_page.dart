import 'package:flutter/material.dart';
import 'package:mobile/screens/app_screens.dart';

class WelcomePage extends StatelessWidget {
  const WelcomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SizedBox(
        width: double.infinity,
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            spacing: 16,
            children: [
              Column(
                children: [
                  Text(
                    "Bem vindo ao Rotineiro!",
                    style: TextStyle(fontSize: 24, fontWeight: FontWeight.w600),
                  ),
                  Text(
                    "Gerencie suas tarefas e rotinas de um jeito simples e fácil!",
                  ),
                ],
              ),
              Column(
                spacing: 8,
                children: [
                  Row(
                    children: [
                      Expanded(
                        child: ElevatedButton(
                          onPressed: () {
                            Navigator.of(context).pushNamed(Appscreens().LOGIN);
                          },
                          child: Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Text("Login"),
                          ),
                        ),
                      ),
                    ],
                  ),
                  Row(
                    children: [
                      Expanded(
                        child: ElevatedButton(
                          onPressed: () {
                            Navigator.of(
                              context,
                            ).pushNamed(Appscreens().REGISTER);
                          },
                          child: Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Text("Cadastro"),
                          ),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
