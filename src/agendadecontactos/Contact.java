package agendadecontactos;

public class Contact {

    private int id;
    private String name;
    private String cellphone;
    private String email;

    public Contact(int lastId, String name, String cellphone, String email) {
        setId(lastId);
        setName(name);
        setCellphone(cellphone);
        setEmail(email);
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    private void setEmail(String email) {
        this.email = email;
    }
    
    private void setId(int lastId) {
        this.id = lastId;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void modifyId(int newId) {
        this.id = newId;
    }
    
    public void modifyName(String newName) {
        this.name = newName;
    }
    
    public void modifyCellphone(String newCellphone) {
        this.cellphone = newCellphone;
    }
    
    public void modifyEmail(String newEmail) {
        this.email = newEmail;
    }
    
    public String toCsv() {
        return getId() + "," + getName()+ "," + getCellphone()+ "," + getEmail();
    }
    
    public String toList() {
        return getName() + "\n" + getCellphone() + "\n" + getEmail();
    }

    @Override
    public String toString() {
        return "\nID: " + getId() + "\nName: " + getName() + "\nCellphone: " + getCellphone() + "\nEmail: " + getEmail() + "\n";
    }
}
