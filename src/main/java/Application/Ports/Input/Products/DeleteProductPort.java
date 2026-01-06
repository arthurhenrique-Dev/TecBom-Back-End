package Application.Ports.Input.Products;

import java.util.List;
import java.util.UUID;

public interface DeleteProductPort {

    void deleteList(List<UUID> ids);
}
