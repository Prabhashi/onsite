package com.example.tjr.onsite.ui.common;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.tjr.onsite.R;
import com.example.tjr.onsite.adapter.CommentAdapter;
import com.example.tjr.onsite.app.MenuConstants;
import com.example.tjr.onsite.app.MyVolley;
import com.example.tjr.onsite.controllers.IssueHomeController;
import com.example.tjr.onsite.model.Comment;
import com.example.tjr.onsite.model.DataSource;
import com.example.tjr.onsite.model.DetailedIssue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


//TODO: Add issue type as improvement, modification
//
public class IssueHomeActivity extends AppCompatActivity {

    //views
    private TextView issueName, date, severity, description, reporterName, assignee;
    private EditText commentEditText;
    private ImageView issueImage, stateImage, reporterImage, planImage;
    private RecyclerView commentsRecyclerView;
    private Button addComment;

    //tools
    private ImageLoader imageLoader;
    private CommentAdapter adapter;
    private IssueHomeActivity context = this;

    //variables needed for operations
    private int issueId;
    private DetailedIssue issue;
    private int currentImage = 0;

    private IssueHomeController controller = new IssueHomeController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_home);

        getSupportActionBar().setTitle("Issue Home");
        Intent intent = getIntent();
        issueId = intent.getIntExtra("issueId", 0);

        //initialize views
        issueName = (TextView) findViewById(R.id.txt_issue_home_name);
        date = (TextView) findViewById(R.id.txt_issue_home_date);
        severity = (TextView) findViewById(R.id.txt_issue_home_severity);
        description = (TextView) findViewById(R.id.txt_issue_home_description);
        reporterName = (TextView) findViewById(R.id.txt_issue_home_reporter_name);
        assignee = (TextView) findViewById(R.id.txt_issue_home_assignee_name);
        commentEditText = (EditText) findViewById(R.id.txt_issue_home_comment_text);
        // images
        issueImage = (ImageView) findViewById(R.id.im_switch_issue_home_image);
        stateImage = (ImageView) findViewById(R.id.im_issue_home_resolved);
        reporterImage = (ImageView) findViewById(R.id.im_issue_home_reporter_image);
        planImage = (ImageView) findViewById(R.id.img_issue_home_map);

        addComment = (Button) findViewById(R.id.btn_issue_home_add_comment);
        commentsRecyclerView = (RecyclerView) findViewById(R.id.rec_issue_home_comments);
        ArrayList<Comment> list = new ArrayList<>();

        imageLoader = MyVolley.getImageLoader();

        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize adapter
        adapter = new CommentAdapter(list, getLayoutInflater(), this);
        //
        registerForContextMenu(assignee);
        registerForContextMenu(stateImage);
        registerForContextMenu(issueImage);
        registerForContextMenu(planImage);

        setListeners();
        commentsRecyclerView.setAdapter(adapter);
        //Query and set issue details
        //passes issue id with it
        DataSource.retrieveIssueData(this, issueId);

    }

    Bitmap theBitmap;
    public void updateIssueData(final DetailedIssue issue) {
        this.issue = issue;
        issueName.setText(issue.name);
        date.setText(issue.date);
        severity.setText(issue.severity + " severity");
        description.setText(issue.description);
        reporterName.setText(issue.reporterName);

        if (issue.images != null && issue.images.size() > 0)
            imageLoader.get(issue.images.get(0), ImageLoader.getImageListener(issueImage, 0, 0));
        imageLoader.get(issue.reporterImageUrl,ImageLoader.getImageListener(reporterImage,0,0));
        if(issue.planImageUrl != null) {
            loadPlan(issue.locationX,issue.locationY);
        }

        //setting resolved icon
        if ("resolved".equals(issue.state)) {
            stateImage.setImageResource(R.drawable.ic_check_circle_black_24dp);
            stateImage.setColorFilter(Color.GREEN);
        } else {
            stateImage.setImageResource(R.drawable.ic_warning_black_24dp);
            stateImage.setColorFilter(Color.RED);
        }

        //further decorations
        switch (issue.severity) {
            case "high":
                severity.setTextColor(Color.RED);
                break;
            case "medium":
                severity.setTextColor(Color.YELLOW);
                break;
            default:
                severity.setTextColor(Color.BLUE);

        }

        adapter.setComments(issue.comments);
        adapter.notifyDataSetChanged();

        //set tags
        LinearLayout tagsLayout = (LinearLayout) findViewById(R.id.layout_issues_tags);

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        llp.setMargins(5, 5, 5, 5); // llp.setMargins(left, top, right, bottom);


        for (String s : issue.tags) {
            TextView tag = new TextView(this);
            tag.setText(s);
            tag.setGravity(Gravity.CENTER_VERTICAL);
            tag.setBackgroundColor(Color.rgb(221, 221, 221));
            tag.setPadding(10, 10, 10, 10);
            tag.setLayoutParams(llp);
            tagsLayout.addView(tag);
            //set listener for each tag
            tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO : load issues activity filtering particular tag
                }
            });
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        switch (v.getId()) {
            case R.id.im_issue_home_resolved:
               //groupId , itemId,
                menu.add(Menu.NONE, MenuConstants.ISSUE_HOME_MARK_AS_RESOLVED, 0, "Mark As Resolved");
                break;

            case R.id.img_issue_home_map:
                menu.add(Menu.NONE, MenuConstants.ISSUE_HOME_CHANGE_PLAN_IMAGE, 0, "Edit Floor Plan");
                menu.add(Menu.NONE, MenuConstants.ISSUE_HOME_EDIT_LOCATION, 0, "Edit Location");
                break;

            case R.id.txt_issue_home_assignee_name:
                menu.add(Menu.NONE, MenuConstants.ISSUE_HOME_ASSIGN_PERSON, 0, "Assign Person");
                break;

            case R.id.im_switch_issue_home_image:
                menu.add(Menu.NONE, MenuConstants.ISSUE_HOME_ADD_IMAGE, 0, "Add Image From Gallery");
                menu.add(Menu.NONE, MenuConstants.ISSUE_HOME_ADD_IMAGE + 100, 0, "Add Image From Camera");

                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MenuConstants.ISSUE_HOME_CHANGE_PLAN_IMAGE:
                //TODO : Load plan selector activity
                System.out.println("change plan");
                break;
            case MenuConstants.ISSUE_HOME_MARK_AS_RESOLVED:
                //TODO : Update database as resolved , reload activity
                switch (issue.state) {
                    case "resolved":
                        issue.state = "unresolved";
                        stateImage.setImageResource(R.drawable.ic_warning_black_24dp);
                        stateImage.setColorFilter(Color.RED);
                        break;
                    case "unresolved":
                        issue.state = "unresolved";
                        stateImage.setImageResource(R.drawable.ic_check_circle_black_24dp);
                        stateImage.setColorFilter(Color.GREEN);
                        controller.markAsResolved((issue.issueId));
                        break;
                }

                break;

            case MenuConstants.ISSUE_HOME_ASSIGN_PERSON:
                //TODO : show select user screen
                break;
            case MenuConstants.ISSUE_HOME_EDIT_LOCATION:
                Intent selectorIntent = new Intent(context, LocationSelectorActivity.class);
                selectorIntent.putExtra("imageUrl", issue.planImageUrl);
                startActivityForResult(selectorIntent, 1);
                break;
            case MenuConstants.ISSUE_HOME_ADD_IMAGE:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 50);
                break;
            case MenuConstants.ISSUE_HOME_ADD_IMAGE + 100:
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(i, "Select File"), 51);

                break;


        }
        return super.onContextItemSelected(item);
    }

    private void markAsResolved() {
    }

    private void setListeners() {
        //set listener for user home
        reporterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO : load user profile activity
                System.out.println("load user profile");
            }
        });

        assignee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO : load user profile activity
                System.out.println("load user profile");
            }
        });

        //add comment button
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO : send get request to make a new comment

                System.out.println("load add comment activity");
            }
        });

        issueImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentImage + 1 < issue.images.size())
                    currentImage++;
                else if (currentImage + 1 == issue.images.size())
                    currentImage = 0;

                Intent imgViewIntent = new Intent(context, ImageViewerActivity.class);
                imgViewIntent.putExtra("image_url", issue.images.get(currentImage));
                context.startActivity(imgViewIntent);
                System.out.println("load next image onto view");
            }
        });

        planImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadPlan(data.getFloatExtra("xRatio", 0.0f), data.getFloatExtra("yRatio", 0.0f));
        }
        if (requestCode == 51) {//image from gallery
            Uri selectedImageUri = data.getData();
            String imagepath = getPath(selectedImageUri);
            File imageFile = new File(imagepath);

            //TODO : Upload this file to server

        }
        if (requestCode == 51) { //image from camera
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    private void markPointOnMap(float x, float y) {
        Bitmap bitmap = ((BitmapDrawable) planImage.getDrawable()).getBitmap();
        Bitmap tempBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(tempBitmap);

//Draw the image bitmap into the cavas
        tempCanvas.drawBitmap(bitmap, 0, 0, null);

//Draw everything else you want into the canvas, in this example a rectangle with rounded edges
        //tempCanvas.drawRoundRect(new RectF(x1,y1,x2,y2), 2, 2, myPaint);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);


        float xLocation = x * bitmap.getWidth();
        float yLocation = y * bitmap.getHeight();
        tempCanvas.drawCircle(xLocation, yLocation, 30, paint);
//Attach the canvas to the ImageView
        planImage.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
    }


    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }

    private void loadPlan(final float x, final float y){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if(Looper.myLooper() == null)
                Looper.prepare();

                try {
                    theBitmap = Glide.
                            with(IssueHomeActivity.this).
                            load(issue.planImageUrl).
                            asBitmap().
                            into(-1,-1).
                            get();
                } catch (final ExecutionException e) {
                    System.out.println(e.getMessage());
                } catch (final InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void dummy) {
                if (null != theBitmap) {
                    // The full bitmap should be available here
                    planImage.setImageBitmap(theBitmap);
                    markPointOnMap(x,y);

                };
            }
        }.execute();
    }
}
