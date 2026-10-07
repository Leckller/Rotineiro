class QueryResult<T> {

  int page;     
  int pageSize;   
  int total;      
  int totalPages; 
  T data;

  QueryResult({required this.page, required this.pageSize, required this.total, required this.totalPages, required this.data});

}