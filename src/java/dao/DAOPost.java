package dao;

import entity.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOPost extends DBConnect {

    public Vector<Post> getAllPosts() {
        Vector<Post> vector = new Vector<>();
        String sql = "SELECT * FROM Post";
        try(PreparedStatement stm = con.prepareStatement(sql)){
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                vector.add(new Post(rs.getInt("post_id"), rs.getString("title"), rs.getString("summary"), rs.getString("thumbnail_url"), rs.getString("content"), rs.getBoolean("status")));
            }
            return vector;
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Post getPostDetails(String postId) {
        String sql = "SELECT * FROM Post where post_id = ?";
        try(PreparedStatement stm = con.prepareStatement(sql)){
            stm.setString(1, postId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new Post(rs.getInt("post_id"), rs.getString("title"), rs.getString("summary"), rs.getString("thumbnail_url"), rs.getString("content"), rs.getBoolean("status"));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Vector<Post> getPostsByKeyword(String query){
        Vector<Post> vector = new Vector<>();
        String sql = "SELECT * FROM Post WHERE title LIKE ?";
        try(PreparedStatement stm = con.prepareStatement(sql)){
            stm.setString(1, "%" + query + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                vector.add(new Post(rs.getInt("post_id"), rs.getString("title"), rs.getString("summary"), rs.getString("thumbnail_url"), rs.getString("content"), rs.getBoolean("status")));
            }
            return vector;
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Post getPostByPostId(int postId){
        String sql = "SELECT * FROM Post WHERE post_id = ?";
        try(PreparedStatement stm = con.prepareStatement(sql)){
            stm.setInt(1, postId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return new Post(rs.getInt("post_id"), rs.getString("title"), rs.getString("summary"), rs.getString("thumbnail_url"), rs.getString("content"), rs.getBoolean("status"));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void updatePosts(Post p){
        String sql = "UPDATE Post SET title = ?, summary = ?, thumbnail_url = ?, content = ?, status = ? where post_id = ?";
        try(PreparedStatement stm = con.prepareStatement(sql)){
            stm.setString(1, p.getTitle());
            stm.setString(2, p.getSummary());
            stm.setString(3, p.getThumbnail_url());
            stm.setString(4, p.getContent());
            stm.setBoolean(5, p.getStatus());
            stm.setInt(6, p.getPost_id());
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addPost(Post p){
        String sql = "INSERT INTO post (`title`,`summary`,`thumbnail_url`,`content`,`status`) VALUES (?,?,?,?,?)";
        try(PreparedStatement stm = con.prepareStatement(sql)){
            stm.setString(1, p.getTitle());
            stm.setString(2, p.getSummary());
            stm.setString(3, p.getThumbnail_url());
            stm.setString(4, p.getContent());
            stm.setBoolean(5, p.getStatus());
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deletePosts(int postId){
        String sql = "DELETE FROM Post WHERE post_id = ?";
        try(PreparedStatement stm = con.prepareStatement(sql)){
            stm.setInt(1, postId);
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
