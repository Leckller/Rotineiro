import 'package:flutter/material.dart';
import 'package:mobile/models/routine.dart';
import 'package:mobile/screens/app_screens.dart';

class RoutineCard extends StatelessWidget {
  const RoutineCard({super.key, required this.routine});

  final Routine routine;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 80,
      width: double.infinity,
      child: Card(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16.0),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Expanded(
                child: Row(
                  spacing: 24,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Container(
                      width: 50,
                      height: 50,
                      decoration: BoxDecoration(
                        color: Colors.yellow,
                        borderRadius: BorderRadiusDirectional.all(
                          Radius.circular(8),
                        ),
                      ),
                      child: Icon(Icons.sunny),
                    ),
                    Expanded(
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            routine.title,
                            overflow: TextOverflow.ellipsis,
                            style: TextStyle(
                              fontWeight: FontWeight.w600,
                              fontSize: 18,
                            ),
                          ),
                          Text(
                            "3 tarefas",
                            style: TextStyle(fontSize: 14, color: Colors.grey),
                          ), // Tem que adicionar o vinculo com as tasks e dpois mudar aqui
                        ],
                      ),
                    ),
                  ],
                ),
              ),
              IconButton(onPressed: () {
                Navigator.of(context).pushNamed(Appscreens().routineDetails, arguments: routine);
              }, icon: Icon(Icons.chevron_right, color: Colors.grey,)),
            ],
          ),
        ),
      ),
    );
  }
}
