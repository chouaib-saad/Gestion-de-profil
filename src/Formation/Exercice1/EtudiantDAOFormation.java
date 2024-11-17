package Formation.Exercice1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAOFormation implements EtudiantDAOCRUDFormation {

    Connection conn = null;

    public EtudiantDAOFormation(String DB_URL, String DB_USER, String DB_PASS) {
        conn = ConnectionTp4.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    @Override
    public int insertEtudiant(String nom, String prenom, int cin, double moyenne) {
        Statement st = null;
        try {
            if (conn != null) {
                st = conn.createStatement();
                String req = "INSERT INTO Etudiant VALUES ('" + nom + "', '" + prenom + "', " + cin + ", " + moyenne + ")";
                int a = st.executeUpdate(req);
                System.out.println("Nombre de lignes affectées : " + a);
                return a; // retourner le nombre de lignes affectées par la requête
            } else {
                System.out.println("La connexion à la base de données est nulle.");
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Erreur d'exécution de la requête : " + ex.getMessage());
            return 0;
        }
    }

    @Override
    public void afficheAll(String req) {
        Statement st = null;
        try {
            if (conn != null) {
                st = conn.createStatement();
                ResultSet rs = st.executeQuery(req); //rs pointeur de memoire contient les resultats: plsrs lignes contenant plsrs colonnes
                while (rs.next()) {
                    String nom = rs.getString(1);
                    String prenom = rs.getString(2);
                    int cin = rs.getInt(3);
                    double moy = rs.getDouble(4);
                    System.out.println(nom + " " + prenom + " " + cin + " " + moy);
                }
            } else {
                System.out.println("La connexion à la base de données est nulle.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur connexion " + ex.getMessage());
        }
    }

    public ResultSet selection(String req) {
        try {
            if (conn != null) {
                Statement st = conn.createStatement();
                return st.executeQuery(req);
            } else {
                System.out.println("La connexion à la base de données est nulle.");
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la sélection : " + ex.getMessage());
            return null;
        }
    }


    public void afficherResultSet(ResultSet rs) {
        try {
            while (rs.next()) {
                String n = rs.getString(1);
                String p = rs.getString(2);
                int cin = rs.getInt(3);
                double moy = rs.getDouble(4);
                System.out.println(n + ' ' + p + ' ' + cin + ' ' + moy);
            }
        } catch (SQLException e) {
            System.out.println("Erreur affichage " + e.getMessage());
        }
    }
    @Override
    public int supprimerEtudiant(int cin) {
        String req = "DELETE FROM etudiant WHERE cin = ?";
        PreparedStatement ps = null;
        int lignesupprimees = 0;
        try {
            ps = conn.prepareStatement(req);
            ps.setInt(1, cin);
            lignesupprimees = ps.executeUpdate();
            System.out.println(lignesupprimees + " ligne(s) supprimée(s).");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression " + e.getMessage());
        }
        return lignesupprimees;
    }
    @Override
    public int modifierEtudiant(String nom, String prenom, Integer cin, Double moyenne) {
        if (cin == null) {
            System.out.println("Le numéro d'identification (cin) de l'étudiant est requis pour la modification.");
            return 0; // Ou une autre valeur pour indiquer une erreur
        }
        StringBuilder reqBuilder = new StringBuilder("UPDATE etudiant SET");
        List<Object> params = new ArrayList<>();
        int paramCount = 0;
        if (nom != null) {
            reqBuilder.append(" nom = ?");
            params.add(nom);
            paramCount++;
        }
        if (prenom != null) {
            if (paramCount > 0) {
                reqBuilder.append(",");
            }
            reqBuilder.append(" prenom = ?");
            params.add(prenom);
            paramCount++;
        }
        if (moyenne != null) {
            if (paramCount > 0) {
                reqBuilder.append(",");
            }
            reqBuilder.append(" moyenne = ?");
            params.add(moyenne);
            paramCount++;
        }
        if (cin != null) {
            if (paramCount > 0) {
                reqBuilder.append(",");
            }
            reqBuilder.append(" cin = ?");
            params.add(cin);
        }
        reqBuilder.append(" WHERE cin = ?");
        params.add(cin); // Ajout du cin pour la clause WHERE
        String req = reqBuilder.toString();
        PreparedStatement ps = null;
        int rowsUpdated = 0;
        try {
            ps = conn.prepareStatement(req);
            for (int i = 0; i < params.size(); i++) {
                Object value = params.get(i);
                if (value instanceof String) {
                    ps.setString(i + 1, (String) value);
                } else if (value instanceof Integer) {
                    ps.setInt(i + 1, (Integer) value);
                } else if (value instanceof Double) {
                    ps.setDouble(i + 1, (Double) value);
                }
            }
            // Exécution de la requête
            rowsUpdated = ps.executeUpdate();
            System.out.println(rowsUpdated + " row(s) updated successfully.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'étudiant : " + e.getMessage());
        }
        return rowsUpdated;
    }

    @Override
    public void EtudiantMoyenneSup10() {
        String req = "select * from etudiant where moyenne > 10 ";

        try {
            if (conn != null) {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(req); //rs pointeur de memoire contient les resultats: plsrs lignes contenant plsrs colonnes
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String nom = rs.getString(2);
                    String prenom = rs.getString(3);
                    double moy = rs.getDouble(4);
                    System.out.println(nom + " " + prenom + " " + moy);
                }
            }else {
                System.out.println("La connexion à la base de données est nulle.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la sélection : " + ex.getMessage());

        }

    }

}