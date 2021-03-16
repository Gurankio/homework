package gurankio;

import gurankio.io.data.Persistent;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Mailbox {

    private final List<Mail> mails;

    public Mailbox() {
        mails = Persistent.list(Mail.class);
        mails.sort(Comparator.comparing(Mail::getDateTime).reversed());
    }

    public void aggiungiMail(Mail mail) {
        mails.add(mail);
        mails.sort(Comparator.comparing(Mail::getDateTime).reversed());
    }

    public Mail get(int index) {
        return mails.get(index);
    }

    public void eliminaMail(int index) {
        mails.remove(index).delete();
    }

    public List<Mail> ricercaOggetto(String s) {
        return mails.stream().filter(mail -> mail.getOggetto().contains(s)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        DecimalFormat format = new DecimalFormat();
        format.setMinimumIntegerDigits(Integer.toString(mails.size()).length());
        return IntStream.range(0, mails.size()).map(i -> mails.size() - i - 1).mapToObj(i -> String.format(" + (%s): %s", format.format(i), mails.get(i))).collect(Collectors.joining("\n"));
    }
}
