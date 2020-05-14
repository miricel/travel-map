package chat.Client;

import javax.swing.*;

public interface UserStatusListener  {
    public abstract void online(String username);
    public abstract void offline(String username);


}
