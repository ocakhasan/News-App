package com.example.hasanocakhomework3;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by atanaltay on 28/03/2017.
 */

public class NewsItem implements Serializable{

    private String title;
    private transient Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private String text;
    private String category;


    private String imageId;
    private Date newsDate;
    private int id;
    //private List<CommentItem> comments;


    public NewsItem() {
    }

    public NewsItem(int id, String title, String text, String imageId, Date newsDate, String category) {
        this.title = title;
        this.text = text;
        this.imageId = imageId;
        this.newsDate = newsDate;
        this.id = id;
        //comments = new ArrayList<>();
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    /*
    public static List<NewsItem> getFavourites(){


        NewsItem item1 = new NewsItem(0,"In Protests, Kremlin Fears a Young Generation Stirring","MOSCOW — The weekend anticorruption protests that roiled Moscow and nearly 100 Russian towns clearly rattled the Kremlin, unprepared for their size and seeming spontaneity. But perhaps the biggest surprise, even to protest leaders themselves, was the youthfulness of the crowds.\n" +
                "\n" +
                "A previously apathetic generation of people in their teens and 20s, most of them knowing nothing but 17 years of rule by Vladimir V. Putin, was the most striking face of the demonstrations, the biggest in years.\n" +
                "\n" +
                "It is far from clear whether their enthusiasm for challenging the authorities, which has suddenly provided adrenaline to Russia’s beaten-down opposition, will be short-lived or points to a new era. Nor is it clear whether the object of the anger — blatant and unabashed corruption — will infect the popularity of Mr. Putin.\n" +
                "\n" +
                "But the harshness of the response to the protests on Sunday — hundreds of people were arrested, in many cases simply for showing up — suggested that Mr. Putin’s hierarchy was taking no chances.\n" +
                "\n" +
                "Continue reading the main story\n" +
                "RELATED COVERAGE\n" +
                "\n" +
                "\n" +
                "Aleksei Navalny, Russian Opposition Leader, Receives 15-Day Sentence MARCH 27, 2017\n" +
                "\n" +
                "How Putin Keeps Protesters at Bay in Russia MARCH 27, 2017\n" +
                "RECENT COMMENTS\n" +
                "\n" +
                "Kuzma 2 hours ago\n" +
                "\"Young people, he said, “just want to live like normal, modern people in the rest of Europe.”\"And they actually do. These protests are not...\n" +
                "Daniel 2 hours ago\n" +
                "The troubling question is who or what would replace Putin. As we know from Libya and Iraq, toppling a dictator can makes things far worse...\n" +
                "Eternal Vigilance 2 hours ago\n" +
                "In Russia, where the state controls the media, Donald Trump must be envious. Both Vladimir Putin and Donald Trump believe that a free press...\n" +
                "SEE ALL COMMENTS  WRITE A COMMENT\n" +
                "Artyom Troitsky, a Russian journalist and concert promoter who for years has tracked Russian youth culture, said the fact that so many young people took part in the protests in Moscow and elsewhere “is exceptionally important.”", R.drawable.mosul4,new Date());

        NewsItem item2 = new NewsItem(1,"Canada Today: Restaurant Resentment and Cardboard Trudeaus","Each week, Canada Today mixes the Times’s recent Canada-related coverage with back stories and analysis from our reporters along with opinions from our readers.\n" +
                "\n" +
                "Sam Sifton writes a Times newsletter, his recipes appear on Sundays in The New York Times Magazine and, because he apparently doesn’t have enough to do, he is also the editor in charge of all things food related at the Times. He recently noticed that there was growing ferment on Twitter about the new restaurant at Montreal’s government-owned casino and asked me to look into it. The product of that request appeared in the Food section this week.\n" +
                "\n" +
                "Over the past couple of decades, the number of innovative locally owned restaurants has grown in Montreal and other Canadian cities, and they are gaining national and international recognition. So there was dismay among many of the chefs at those restaurants when the province’s lottery agency, Loto Quebec, which oversees the casino, turned to an acclaimed chef from Paris to create a signature restaurant for its casino. Adding to the insult, in their eyes, it appears that the lottery agency is heavily subsidizing the L’Atelier de Joël Robuchon.",R.drawable.canada,new Date());


        List<NewsItem> data = new ArrayList<>();
        data.add(item1);
        data.add(item2);
        Log.i("DEV","Favs called...");
        return data;
    }

    public static List<NewsItem> getAllNews(){


        NewsItem item1 = new NewsItem(0,"In Protests, Kremlin Fears a Young Generation Stirring","MOSCOW — The weekend anticorruption protests that roiled Moscow and nearly 100 Russian towns clearly rattled the Kremlin, unprepared for their size and seeming spontaneity. But perhaps the biggest surprise, even to protest leaders themselves, was the youthfulness of the crowds.\n" +
                "\n" +
                "A previously apathetic generation of people in their teens and 20s, most of them knowing nothing but 17 years of rule by Vladimir V. Putin, was the most striking face of the demonstrations, the biggest in years.\n" +
                "\n" +
                "It is far from clear whether their enthusiasm for challenging the authorities, which has suddenly provided adrenaline to Russia’s beaten-down opposition, will be short-lived or points to a new era. Nor is it clear whether the object of the anger — blatant and unabashed corruption — will infect the popularity of Mr. Putin.\n" +
                "\n" +
                "But the harshness of the response to the protests on Sunday — hundreds of people were arrested, in many cases simply for showing up — suggested that Mr. Putin’s hierarchy was taking no chances.\n" +
                "\n" +
                "Continue reading the main story\n" +
                "RELATED COVERAGE\n" +
                "\n" +
                "\n" +
                "Aleksei Navalny, Russian Opposition Leader, Receives 15-Day Sentence MARCH 27, 2017\n" +
                "\n" +
                "How Putin Keeps Protesters at Bay in Russia MARCH 27, 2017\n" +
                "RECENT COMMENTS\n" +
                "\n" +
                "Kuzma 2 hours ago\n" +
                "\"Young people, he said, “just want to live like normal, modern people in the rest of Europe.”\"And they actually do. These protests are not...\n" +
                "Daniel 2 hours ago\n" +
                "The troubling question is who or what would replace Putin. As we know from Libya and Iraq, toppling a dictator can makes things far worse...\n" +
                "Eternal Vigilance 2 hours ago\n" +
                "In Russia, where the state controls the media, Donald Trump must be envious. Both Vladimir Putin and Donald Trump believe that a free press...\n" +
                "SEE ALL COMMENTS  WRITE A COMMENT\n" +
                "Artyom Troitsky, a Russian journalist and concert promoter who for years has tracked Russian youth culture, said the fact that so many young people took part in the protests in Moscow and elsewhere “is exceptionally important.”",R.drawable.mosul4,new Date());

        NewsItem item2 = new NewsItem(1,"Canada Today: Restaurant Resentment and Cardboard Trudeaus","Each week, Canada Today mixes the Times’s recent Canada-related coverage with back stories and analysis from our reporters along with opinions from our readers.\n" +
                "\n" +
                "Sam Sifton writes a Times newsletter, his recipes appear on Sundays in The New York Times Magazine and, because he apparently doesn’t have enough to do, he is also the editor in charge of all things food related at the Times. He recently noticed that there was growing ferment on Twitter about the new restaurant at Montreal’s government-owned casino and asked me to look into it. The product of that request appeared in the Food section this week.\n" +
                "\n" +
                "Over the past couple of decades, the number of innovative locally owned restaurants has grown in Montreal and other Canadian cities, and they are gaining national and international recognition. So there was dismay among many of the chefs at those restaurants when the province’s lottery agency, Loto Quebec, which oversees the casino, turned to an acclaimed chef from Paris to create a signature restaurant for its casino. Adding to the insult, in their eyes, it appears that the lottery agency is heavily subsidizing the L’Atelier de Joël Robuchon.",R.drawable.canada,new Date());

        NewsItem item3 = new NewsItem(2,"Miners Found a 706-Carat Diamond in Sierra Leone. Who Should Get the Profit?","DAKAR, Senegal — Fist-size and lumpy, the rock that a team of miners came upon recently in the diamond fields of Sierra Leone was orange with red speckles that looked like tiny droplets of palm oil.\n" +
                "\n" +
                "They almost tossed it aside. The rock didn’t seem like the kind of gem that diggers typically unearth in a nation with a reputation for having some of the highest quality and most transparent diamonds in the world. But the rock was unusual enough to take to a local diamond dealer.\n" +
                "\n" +
                "“The look on his face when he saw the rock made me believe that we discovered something extraordinary,” said the Rev. Emmanuel Momoh, a pastor who runs the team of diggers who found the rock.",
                R.drawable.diamond,new Date());

        NewsItem item4 = new NewsItem(3,"North Korea’s Rising Ambition Seen in Bid to Breach Global Banks","When hackers associated with North Korea tried to break into Polish banks late last year they left a trail of information about their apparent intentions to steal money from more than 100 organizations around the world, according to security researchers.\n" +
                "\n" +
                "A list of internet protocol addresses, which was supplied by the security researchers and analyzed by The New York Times, showed that the hacking targets included institutions like the World Bank, the European Central Bank and big American companies including Bank of America.\n" +
                "\n" +
                "While some of the Polish banks took the hackers’ bait, the scheme was detected fairly quickly, and there is no evidence that any money was stolen from the intended targets. Yet security researchers said the hit list, found embedded in the code of the attack on more than 20 Polish banks, underlines how sophisticated the capabilities of North Korean hackers have become. Their goals have now turned financial, along with efforts to spread propaganda and heist data and to disrupt government and news websites in countries considered enemies.",R.drawable.korea,new Date());

        List<NewsItem> data = new ArrayList<>();
        data.add(item1);
        data.add(item2);
        data.add(item3);
        data.add(item4);
        return data;
    }

    public static List<CommentItem> getCommentsByNewsId(int newsid){
        List<CommentItem> comments = new ArrayList<>();
        if(newsid==0){
            CommentItem i1 = new CommentItem(0,"Jack Harris","Nice news, but can have more info on the subject if possible.");
            CommentItem i2 = new CommentItem(1,"Henry Johns","Nice news, but can have more info on the subject if possible.");
            comments.add(i1);
            comments.add(i2);
        }else if(newsid==1){
            CommentItem i3 = new CommentItem(2,"Patrick Williams","Long long long sample comment. More text can be added to display comment");

            CommentItem i4 = new CommentItem(3,"Jessica Denis","Nice news, but can have more info on the subject if possible.");
            comments.add(i3);
            comments.add(i4);
        }else if(newsid==2){
            CommentItem i5 = new CommentItem(4,"Gary Niceguy","Nice news, but can have more info on the subject if possible.");
            comments.add(i5);

        }

        return comments;
    }

     */

}
