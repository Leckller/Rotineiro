class Task {

  int id;
  String title;
  String description;

  Task({required this.id, required this.title, required this.description});

  factory Task.fromJson(Map<String, dynamic> json) {
    return Task(
      id: json['ID'],
      title: json['Title'],
      description: json['Description'],
    );
  }

}