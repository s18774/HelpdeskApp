package pl.wroblewski.helpdeskapp.exceptions;

public class DeviceAlreadyAttachedException extends Exception {
    public DeviceAlreadyAttachedException() {
        super("Device is already attached to user");
    }
}
