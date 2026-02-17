package transform;

public class XmlTransformer implements Transformer {

    @Override
    public String transform(String record) {
        return "<msg>" + record + "</msg>";
    }
}
