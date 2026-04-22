package controle.matriculas.educontrol.bean;

import controle.matriculas.educontrol.model.Disciplina;
import controle.matriculas.educontrol.util.HibernateUtil;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hibernate.Session;

@FacesConverter(value = "disciplinaConverter", forClass = Disciplina.class)
public class DisciplinaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.trim().isEmpty()) return null;

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Disciplina.class, Integer.valueOf(value));
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
        if (obj == null) return "";

        if (obj instanceof Disciplina) {
            Disciplina d = (Disciplina) obj;
            return d.getCodigo() != null ? d.getCodigo().toString() : "";
        }

        return "";
    }
}