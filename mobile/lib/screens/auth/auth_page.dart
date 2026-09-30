import 'package:flutter/material.dart';
import 'package:mobile/providers/auth_provider.dart';
import 'package:provider/provider.dart';

class AuthPage extends StatefulWidget {
  const AuthPage({super.key});

  @override
  State<AuthPage> createState() => _AuthPageState();
}

class _AuthPageState extends State<AuthPage> {
  final _formKey = GlobalKey<FormState>();
  TextEditingController emailController = TextEditingController();
  TextEditingController nameController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  bool isLogin = true;

  @override
  void dispose() {
    emailController.dispose();
    nameController.dispose();
    passwordController.dispose();
    super.dispose();
  }

  Future<void> onSave() async {
    final bool isValid = _formKey.currentState?.validate() ?? false;
    if (!isValid) {
      return;
    }

    final auth = context.read<AuthProvider>();

    if (isLogin) {
      await auth.login(emailController.text.trim(), passwordController.text);
    } else {
      await auth.register(
        emailController.text.trim(),
        nameController.text.trim(),
        passwordController.text,
      );
    }

    if (!mounted) return;

    if (auth.error != null) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text(auth.error!)));
    }
  }

  String? emailValidator(String? value) {
    if (value == null || value.isEmpty) {
      return 'O campo é obrigatório.';
    }
    if (value.length != value.replaceAll(' ', '').length) {
      return 'O email não deve conter espaços';
    }
    if (!value.contains('@')) {
      return 'Email inválido';
    }
    return null;
  }

  String? nameValidator(String? value) {
    if (value == null || value.isEmpty) {
      return 'O campo é obrigatório.';
    }
    if (value.length != value.replaceAll(' ', '').length) {
      return 'O nome não deve conter espaços';
    }
    if (value.length >= 3) {
      return 'O nome deve ter pelo menos 3 caracteres';
    }
    return null;
  }

  String? passwordValidator(String? value) {
    if (value == null || value.isEmpty) {
      return 'O campo é obrigatório.';
    }
    if (value.length != value.replaceAll(' ', '').length) {
      return 'A senha não deve conter espaços';
    }
    if (value.length <= 4) {
      return 'A senha deve ter pelo menos 5 caracteres';
    }
    return null;
  }

  @override
  Widget build(BuildContext context) {
    final loading = context.watch<AuthProvider>().loading;

    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            SizedBox(
              width: double.infinity,
              child: SegmentedButton<bool>(
                segments: const [
                  ButtonSegment<bool>(
                    icon: Icon(Icons.login),
                    value: true,
                    label: Text('Login'),
                  ),
                  ButtonSegment<bool>(
                    icon: Icon(Icons.person_add),
                    value: false,
                    label: Text('Cadastro'),
                  ),
                ],
                selected: {isLogin},
                onSelectionChanged: (_) {
                  setState(() {
                    isLogin = !isLogin;
                  });
                },
              ),
            ),
            const SizedBox(height: 50),
            Expanded(
              child: Form(
                key: _formKey,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Column(
                      children: [
                        TextFormField(
                          controller: emailController,
                          decoration: const InputDecoration(
                            icon: Icon(Icons.email),
                            labelText: 'Email *',
                          ),
                          validator: (value) => emailValidator(value),
                        ),

                        if (!isLogin)
                          TextFormField(
                            controller: nameController,
                            decoration: const InputDecoration(
                              icon: Icon(Icons.person),
                              labelText: 'Nome *',
                            ),
                            validator: (value) => nameValidator(value),
                          ),

                        TextFormField(
                          controller: passwordController,
                          obscureText: true,
                          decoration: const InputDecoration(
                            icon: Icon(Icons.lock),
                            labelText: 'Senha *',
                          ),
                          validator: (value) => passwordValidator(value),
                        ),
                      ],
                    ),
                    const SizedBox(height: 50),
                    Row(
                      children: [
                        Expanded(
                          child: ElevatedButton(
                            onPressed: loading ? null : onSave,
                            child: loading
                                ? const SizedBox(
                                    height: 20,
                                    width: 20,
                                    child: CircularProgressIndicator(
                                      strokeWidth: 2,
                                    ),
                                  )
                                : Text(isLogin ? 'Entrar' : 'Cadastrar'),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
