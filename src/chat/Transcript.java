
package chat;

import java.util.ArrayList;


/**
 *
 * @author Liza Girsova
 */
public class Transcript {
    
    ArrayList<String> comments = new ArrayList<>();
    String comment;
    int commentCount = 0;

    
    public synchronized void addComment(String commentWithHandle){
    comments.add(commentWithHandle);
    setCommentCount(comments.size());
    }
    
    public synchronized String getComments(int n){
    comment = comments.get(n);
    return comment;    
    }
    
    public synchronized int getCommentCount(){
    return commentCount;    
    }
    
    public synchronized void setCommentCount(int commentCount){
    this.commentCount = commentCount;
    }
    
    
}
