package controle.matriculas.educontrol.bean;

import controle.matriculas.educontrol.model.Pessoa;
import controle.matriculas.educontrol.util.HibernateUtil;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hibernate.Session;

@FacesConverter(value = "pessoaConverter", forClass = Pessoa.class)
public class PessoaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.trim().isEmpty()) return null;

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Pessoa.class, Integer.valueOf(value));
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        if (obj == null) return "";

        if (obj instanceof Pessoa) {
            Pessoa p = (Pessoa) obj;
            return p.getIdPessoa() != null ? p.getIdPessoa().toString() : "";
        }

        return "";
    }
}