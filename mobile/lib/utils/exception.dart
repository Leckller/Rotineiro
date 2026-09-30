class ApiException implements Exception {

  final int statusCode;
  final String message;

  ApiException({required this.statusCode, required this.message});

}

class NetworkException implements Exception {
  final String message;

  NetworkException({
    required this.message,
  });

  @override
  String toString() => message;
}