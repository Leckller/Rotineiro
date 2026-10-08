import 'dart:async';

import 'package:flutter/material.dart';

class ExecutionScreen extends StatefulWidget {
  const ExecutionScreen({super.key});

  @override
  State<ExecutionScreen> createState() => _ExecutionScreenState();
}

class _ExecutionScreenState extends State<ExecutionScreen> {
  Timer? _timer;
  int _segundos = 0;

  bool get _rodando => _timer?.isActive ?? false;

  void _iniciar() {
    _timer = Timer.periodic(const Duration(seconds: 1), (_) {
      setState(() => _segundos++);
    });
    setState(() {});
  }

  void _pausar() {
    _timer?.cancel();
    setState(() {});
  }

  void _zerar() {
    _timer?.cancel();
    setState(() => _segundos = 0);
  }

  String _formatar(int total) {
    final h = (total ~/ 3600).toString().padLeft(2, '0');
    final m = ((total % 3600) ~/ 60).toString().padLeft(2, '0');
    final s = (total % 60).toString().padLeft(2, '0');
    return '$h:$m:$s';
  }

  void nextTask() {
    // Validar se é a última, caso contrário salvar a data de conclusão no backe ir para a próxima tarefa.
    _zerar();
  }

  @override
  void dispose() {
    super.dispose();
    _timer?.cancel();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          style: IconButton.styleFrom(backgroundColor: Colors.blueAccent),
          onPressed: () {
            // Chamar uma requisição para pausar no backend e o usuário poder voltar dpois
            // lembrando que caso ele volte no dia seguinte deve exibir um modal informando isso e perguntando
            // se quer salvar o que tinha sido feito no histórico e reiniciar a rotina! essa "validação" vai ficar
            // aq no cliente
            Navigator.of(context).pop();
          },
          icon: Icon(Icons.close, color: Colors.white),
        ),
        title: Text("Titulo da tarefa"),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          SizedBox(
            width: 200,
            height: 200,
            child: Stack(
              alignment: Alignment.center,
              children: [
                Positioned.fill(
                  child: CircularProgressIndicator(
                    value: 0.7,
                    strokeWidth: 14,
                    strokeCap: StrokeCap.round,
                    backgroundColor: Colors.black12,
                    color: Colors.blueAccent,
                  ),
                ),
                Text(
                  _formatar(_segundos),
                  style: TextStyle(fontSize: 44, fontWeight: FontWeight.bold),
                ),
              ],
            ),
          ),
          Column(
            children: [
              Text("EXECUTANDO AGORA", style: TextStyle(color: Colors.grey)),
              Text(
                "NOME DA TAREFA",
                style: TextStyle(fontWeight: FontWeight.w600, fontSize: 24),
              ),
            ],
          ),

          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            spacing: 16,
            children: [
              Container(
                decoration: BoxDecoration(
                  color: Colors.grey,
                  borderRadius: BorderRadius.circular(100),
                ),
                child: IconButton(
                  onPressed: () {},
                  icon: Icon(Icons.skip_next_outlined),
                ),
              ),
              Container(
                width: 60,
                height: 60,
                decoration: BoxDecoration(
                  color: Colors.blueAccent,
                  borderRadius: BorderRadius.circular(100),
                ),
                child: IconButton(
                  onPressed: _rodando ? _pausar : _iniciar,
                  icon: Icon(_rodando ? Icons.pause : Icons.play_arrow),
                ),
              ),
              Container(
                decoration: BoxDecoration(
                  color: const Color.fromARGB(255, 47, 113, 49),
                  borderRadius: BorderRadius.circular(100),
                ),
                child: IconButton(
                  onPressed: () {},
                  icon: Icon(Icons.check, color: Colors.greenAccent),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
