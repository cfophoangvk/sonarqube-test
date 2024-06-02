package controller.admin;

import dao.DAOPost;
import entity.Post;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Vector;

/**
 *
 * @author VKHOANG
 */
public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Get DAO layer
        DAOPost daopost = new DAOPost();
        //Get the blogId for viewing details, or the query for searching keywords
        String query = request.getParameter("query");
        //Check if id of the blog is specified to view blog details
        if (query != null && !query.isBlank()) {
            //Query and display blogs that contains keyword
            Vector<Post> vector = daopost.getPostsByKeyword(query);
            request.setAttribute("query", query);
            request.setAttribute("postList", vector);
            request.getRequestDispatcher("admin/postList.jsp").forward(request, response);
        } else {
            //Get all blogs and return to Blog List page
            Vector<Post> vector = daopost.getAllPosts();
            //Pass to the view
            request.setAttribute("postList", vector);
            request.getRequestDispatcher("admin/postList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Get DAO layer
        DAOPost daopost = new DAOPost();
        //Get post mode: update or delete
        String mode = request.getParameter("mode");
        //Get postId
        int postId = 0;
        if (request.getParameter("postId") != null) {
            postId = Integer.parseInt(request.getParameter("postId"));
        }
        switch (mode) {
            case "Edit": {
                //Get post information
                Post p = daopost.getPostByPostId(postId);
                request.setAttribute("postDetail", p);
                //Redirect to post details page
                request.getRequestDispatcher("admin/postDetails.jsp").forward(request, response);
                return;
            }
            case "Enable": {
                //enable post
                //Get post by postId
                Post p = daopost.getPostByPostId(postId);
                //Set status so that post can be visible by other user
                p.setStatus(true);
                daopost.updatePosts(p);
                break;
            }
            case "Disable": {
                //disable post
                //Get post by postId
                Post p = daopost.getPostByPostId(postId);
                //Set status so that post cannot be visible by other user
                p.setStatus(false);
                daopost.updatePosts(p);
                break;
            }
            case "Update": {
                //Get post update information
                String title = request.getParameter("title");
                String summary = request.getParameter("summary");
                String content = request.getParameter("content");
                String thumbnail_url = request.getParameter("thumbnail_url");
                boolean status = Boolean.parseBoolean(request.getParameter("status"));
                Post p = new Post(postId, title, summary, thumbnail_url, content, status);

                //Update
                daopost.updatePosts(p);
                break;
            }
            case "Add": {
                //Get post information
                String title = request.getParameter("title");
                String summary = request.getParameter("summary");
                String content = request.getParameter("content");
//                    String thumbnail_url = request.getParameter("thumbnail_url");
//                    boolean status = Boolean.parseBoolean(request.getParameter("status"));
                Post p = new Post(postId, title, summary, null, content, false);
                //Add
                daopost.addPost(p);
                break;
            }
            case "Delete": {
                daopost.deletePosts(postId);
                break;
            }
            default:
                System.out.println("error occurred");
                break;
        }
        //Run doGet to return to post list page after enable or disable or update post
        doGet(request, response);
    }
}
