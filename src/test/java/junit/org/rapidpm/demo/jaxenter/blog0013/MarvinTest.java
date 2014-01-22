package junit.org.rapidpm.demo.jaxenter.blog0013;

import com.guigarage.marvfx.MarvinFx;
import com.guigarage.marvfx.fixtures.NodeFixture;
import com.guigarage.marvfx.fixtures.impl.TextfieldFixture;
import com.guigarage.marvfx.property.PropertySupervisor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBoxBuilder;
import org.junit.Before;
import org.junit.Test;

import java.lang.management.PlatformManagedObject;

/**
 * Created by Sven Ruppert on 22.01.14.
 */
public class MarvinTest {

    @Before
    public void init(){
        final JFXPanel jfxPanel = new JFXPanel();
        System.out.println("jfxPanel = " + jfxPanel);
    }

    @Test
    public void test1() {
        Button b1 = new Button("Test123");
        MarvinFx.show(b1);
        NodeFixture<Button> b1Fixture = new NodeFixture<Button>(b1);
        b1Fixture.mouse().click();
        b1Fixture.mouse().click(MouseButton.SECONDARY);
    }


    @Test
    public void test2() {
        Button b1 = new Button("Test123");
        MarvinFx.show(b1);
        PropertySupervisor<String> textPropertySupervisor = new PropertySupervisor<>(b1.textProperty());
        textPropertySupervisor.assertValueIsEquals("Test123");

        TextField textField = new TextField("1");
        TextfieldFixture textfieldFixture = new TextfieldFixture(textField);
        PropertySupervisor<String> textSupervisor = textfieldFixture.createTextPropertySupervisor();
        textSupervisor.assertValueIsNotNull();

    }


    @Test
    public void test3() {
        TextField textField = new TextField("1");
        MarvinFx.show(textField);
        TextfieldFixture textfieldFixture = new TextfieldFixture(textField);
        PropertySupervisor<String> textSupervisor = textfieldFixture.createTextPropertySupervisor();

        textSupervisor.assertWillChange();
        textSupervisor.assertWillChangeByDefinedCount(2);
        textSupervisor.assertWillChangeThisWay("7", "14");
        textfieldFixture.setText("7");
        textfieldFixture.setText("14");
        textSupervisor.confirm();
    }

    @Test
    public void test4() {
        final TextField textField = new TextField("1");
        Button button = new Button("Button");
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int value = Integer.parseInt(textField.getText()) * 2;
                textField.setText(value + "");
            }
        });
        MarvinFx.show(VBoxBuilder.create().children(textField, button).build());

        TextfieldFixture textfieldFixture = new TextfieldFixture(textField);
        PropertySupervisor<String> textSupervisor = textfieldFixture.createTextPropertySupervisor();
        NodeFixture<Button> buttonFixture = new NodeFixture<Button>(button);

        textSupervisor.assertValueIsNotNull();
        textSupervisor.assertValueIsEquals("1");
        buttonFixture.mouse().click();
        textSupervisor.assertValueIsEquals("2");

        textSupervisor.assertWillChange();
        textSupervisor.assertWillChangeByDefinedCount(4);
        textSupervisor.assertWillChangeThisWay("4", "8", "16", "32");
        buttonFixture.mouse().click(4);
        textSupervisor.confirm();

        textSupervisor.assertWillNeverChange();
        textSupervisor.confirm();

        textSupervisor.assertWillChange();
        textSupervisor.assertWillChangeByDefinedCount(2);
        textSupervisor.assertWillChangeThisWay("7", "14");
        textfieldFixture.setText("7");
        buttonFixture.mouse().click();
        textSupervisor.confirm();
    }

}
