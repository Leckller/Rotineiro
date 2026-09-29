import 'package:flutter/material.dart';

class AuthPage extends StatefulWidget {
  const AuthPage({super.key});

  @override
  State<AuthPage> createState() => _AuthPageState();
}

class _AuthPageState extends State<AuthPage> {
  bool isLogin = true;
  final _formKey = GlobalKey();

  void togglePage() {
    isLogin = !isLogin;
  }

  List<bool> _selections = [true, false];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Row(
            children: [
              Expanded(
                child: ToggleButtons(
                  isSelected: _selections,
                  onPressed: (int index) {
                    setState(() {
                      _selections = _selections.map((_) => false).toList();
                      _selections[index] = !_selections[index];
                    });
                  },
                  children: [Text("Login"), Text("Cadastro")],
                ),
              ),
            ],
          ),
          Form(
            key: _formKey,
            child: Column(
              children: [
                TextFormField(
                  decoration: const InputDecoration(
                    icon: Icon(Icons.person),
                    hintText: 'What do people call you?',
                    labelText: 'Email *',
                  ),
                  onSaved: (String? value) {},
                  validator: (String? value) {
                    return (value != null && value.contains('@'))
                        ? 'Do not use the @ char.'
                        : null;
                  },
                ),
                if (_selections[0])
                TextFormField(
                  decoration: const InputDecoration(
                    icon: Icon(Icons.person),
                    hintText: 'What do people call you?',
                    labelText: 'Nome *',
                  ),
                  onSaved: (String? value) {},
                  validator: (String? value) {
                    return (value != null && value.contains('@'))
                        ? 'Do not use the @ char.'
                        : null;
                  },
                ),
                TextFormField(
                  decoration: const InputDecoration(
                    icon: Icon(Icons.person),
                    hintText: 'What do people call you?',
                    labelText: 'Senha *',
                  ),
                  onSaved: (String? value) {},
                  validator: (String? value) {
                    return (value != null && value.contains('@'))
                        ? 'Do not use the @ char.'
                        : null;
                  },
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
