package at.technikum.mse.est.poc;

import java.lang.reflect.Field;

import at.technikum.mse.est.FieldLabelBuilder;

public class ReflectionExample {
    public static void main(String[] args) {
        Class testClass = TestClass.class;
        Field[] fields = testClass.getDeclaredFields();

        FieldLabelBuilder fieldLabelBuilder = new FieldLabelBuilder();

        for (Field field : fields) {
            System.out.println("name: " + field.getName());
            System.out.println("type: " + field.getType());
            System.out.println("label: " + fieldLabelBuilder.build(field));
            System.out.println();
        }

    }
}
