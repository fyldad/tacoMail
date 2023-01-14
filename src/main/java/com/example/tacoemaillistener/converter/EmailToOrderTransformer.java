package com.example.tacoemaillistener.converter;

import com.example.tacoemaillistener.data.model.EmailIngredient;
import com.example.tacoemaillistener.data.repository.IngredientRepository;
import com.example.tacoemaillistener.model.EmailOrder;
import com.example.tacoemaillistener.model.EmailTaco;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;
import com.github.wslf.levenshteindistance.LevenshteinCalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<EmailOrder> {

    private final String SUBJECT_KEYWORDS = "TACO ORDER";
    private final IngredientRepository ingredientRepository;

    @Autowired
    public EmailToOrderTransformer(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    protected AbstractIntegrationMessageBuilder<EmailOrder> doTransform(Message mailMessage) {
        EmailOrder order = processPayload(mailMessage);
        return order != null ? MessageBuilder.withPayload(order) : null;
    }

    private EmailOrder processPayload(Message mailMessage) {
        try {
            String subject = mailMessage.getSubject();
            if (subject.toUpperCase().contains(SUBJECT_KEYWORDS)) {
                String mail = ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
                String content = mailMessage.getContent().toString();
                return parseEmailToOrder(mail, content);
            }
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private EmailOrder parseEmailToOrder(String mail, String content) {
        EmailOrder order = new EmailOrder(mail);
        String[] lines = content.split("\\r?\\n");
        for (String line : lines) {
            if (line.trim().length() > 0 && line.contains(":")) {
                String[] lineSplit = line.split(":");
                String tacoName = lineSplit[0].trim();
                String ingredients = lineSplit[1].trim();
                String[] ingredientsSplit = ingredients.split(",");
                List<String> ingredientCodes = new ArrayList<>();
                for (String ingredientName : ingredientsSplit) {
                    String code = lookupIngredientCode(ingredientName.trim());
                    if (code != null) {
                        ingredientCodes.add(code);
                    }
                }
                EmailTaco taco = new EmailTaco(tacoName);
                taco.setIngredients(ingredientCodes);
                order.addTaco(taco);
            }
        }
        return order;
    }

    private String lookupIngredientCode(String ingredientName) {
        for (EmailIngredient ingredient : ingredientRepository.findAll()) {
            String ucIngredientName = ingredientName.toUpperCase();
            LevenshteinCalculator calculator = new LevenshteinCalculator();
            if (calculator.getLevenshteinDistance(ucIngredientName, ingredient.getName()) < 3 ||
                    ucIngredientName.contains(ingredient.getName()) ||
                    ingredient.getName().contains(ucIngredientName)) {
                return ingredient.getId();
            }
        }
        return null;
    }
}
