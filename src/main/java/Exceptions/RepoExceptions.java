package Exceptions;



public class RepoExceptions extends RuntimeException{
    public RepoExceptions(){}

    public RepoExceptions(String message){
        super(message);
    }

    public RepoExceptions(Exception ex){
        super(ex);
    }
}
