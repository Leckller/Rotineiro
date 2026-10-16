import 'package:flutter/material.dart';
import 'package:mobile/models/task.dart';
import 'package:mobile/screens/app_screens.dart';

class TaskCard extends StatelessWidget {
  const TaskCard({super.key, required this.task});

  final Task task;

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
                  spacing: 16,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Container(
                      width: 50,
                      height: 50,
                      decoration: BoxDecoration(
                        color: Colors.blueAccent,
                        borderRadius: BorderRadiusDirectional.all(
                          Radius.circular(100),
                        ),
                      ),
                      child: IconButton(
                        icon: Icon(Icons.play_arrow, color: Colors.white,),
                        onPressed: () {
                          Navigator.of(
                            context,
                          ).pushNamed(Appscreens().execution, arguments: task);
                        },
                      ),
                    ),
                    Container(color: Colors.black12, width: 1, height: 50),
                    Expanded(
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            task.title,
                            overflow: TextOverflow.ellipsis,
                            style: TextStyle(
                              fontWeight: FontWeight.w600,
                              fontSize: 18,
                            ),
                          ),
                          Text(
                            task.description,
                            style: TextStyle(fontSize: 14, color: Colors.grey),
                          ), // Tem que adicionar o vinculo com as tasks e dpois mudar aqui
                        ],
                      ),
                    ),
                  ],
                ),
              ),
              IconButton(
                onPressed: () {
                  Navigator.of(
                    context,
                  ).pushNamed(Appscreens().taskDetails, arguments: task);
                },
                icon: Icon(Icons.chevron_right, color: Colors.grey),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
