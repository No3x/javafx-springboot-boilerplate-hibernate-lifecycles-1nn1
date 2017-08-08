package hello.view;

import com.google.common.collect.Streams;
import hello.Customer;
import hello.CustomerRepository;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by No3x on 08.08.2017.
 */
@Component
public class ListCustomersComponent extends GridPane {

    private CustomerRepository customerRepository;
    private ListView<String> customerListView;

    @Autowired
    ListCustomersComponent(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.customerListView = new ListView<>();
        initComponent();
    }

    private void initComponent() {
        this.add(customerListView, 0, 1);
        List<String> names = Streams.stream(customerRepository.findAll()).map(Customer::toString).collect(Collectors.toList());
        customerListView.getItems().setAll(names);
    }

}
