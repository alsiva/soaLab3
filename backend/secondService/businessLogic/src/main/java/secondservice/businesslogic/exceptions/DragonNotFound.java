package secondservice.businesslogic.exceptions;

public class DragonNotFound extends Exception {
    public DragonNotFound() {
        super("No Dragon found");
    }
}
