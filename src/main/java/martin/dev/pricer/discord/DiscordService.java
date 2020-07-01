package martin.dev.pricer.discord;

import martin.dev.pricer.data.model.Deal;
import martin.dev.pricer.data.model.Price;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

import java.io.IOException;
import java.util.stream.IntStream;

public class DiscordService {
    private final JDA api;
    //                             559492315371274272
    private final long channelId = 559714207889621022L;

    public DiscordService(JDA api) {
        this.api = api;
    }

    public void sendMessage(String content) {
        TextChannel channel = api.getTextChannelById(this.channelId);
        if (channel != null) {
            channel.sendMessage(content).queue();
        }
    }

    public void sendEmbeddedWithImage(Deal deal) {
        TextChannel channel = api.getTextChannelById(this.channelId);

        if (channel != null) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
//            embedBuilder.setTitle(deal.getItem().getName(), "http://51.83.87.167:8082/item/" + deal.getItem().getId())
            embedBuilder.setTitle(deal.getItem().getName(), deal.getItem().getUrl())
                    .setThumbnail(deal.getItem().getUrlObject().getStore().getLogo())
//                    .setImage(deal.getItem().getImg())
                    .addField("Price", "" + deal.getItem().getLastPrice(), true)
                    .addField("Delta", "" + deal.getItem().getLastDelta() + "%", true)
                    .addField("Average Price", "" + deal.getItem().getAvgPrice(), true)
                    .addField("Max Price", "" + deal.getItem().getMaxPrice(), true)
                    .addField("Min Price", "" + deal.getItem().getMinPrice(), true);

            try {
                embedBuilder.setImage(deal.getItem().getImg());
            } catch (Exception e) {
                System.out.println(deal.getItem().getImg());
                System.out.println("img url invalid");
                if (deal.getItem().getImg().startsWith("//")) {
                    embedBuilder.setImage("http://" + deal.getItem().getImg().replace("//", ""));
                }
            }

            channel.sendMessage(embedBuilder.build()).queue();
        }
    }

    public void sendEmbeddedWithoutImage(Deal deal) {
        TextChannel channel = api.getTextChannelById(this.channelId);

        if (channel != null) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
//            embedBuilder.setTitle(deal.getItem().getName(), "http://51.83.87.167:8082/item/" + deal.getItem().getId())
            embedBuilder.setTitle(deal.getItem().getName(), deal.getItem().getUrl())
                    .setThumbnail(deal.getItem().getUrlObject().getStore().getLogo())
                    .addField("Price", "" + deal.getItem().getLastPrice(), true)
                    .addField("Delta", "" + deal.getItem().getLastDelta() + "%", true)
                    .addField("Average Price", "" + deal.getItem().getAvgPrice(), true)
                    .addField("Max Price", "" + deal.getItem().getMaxPrice(), true)
                    .addField("Min Price", "" + deal.getItem().getMinPrice(), true);
            channel.sendMessage(embedBuilder.build()).queue();
        }
    }

    public void buildChart(Deal deal) throws IOException {

        double[] dates = IntStream.range(1, deal.getItem().getPrices().size() + 1).mapToDouble(value -> value + 0.0).toArray();

        double[] prices = deal.getItem().getPrices().stream().mapToDouble(Price::getPrice).toArray();

        // Create Chart
        XYChart chart = QuickChart.getChart(deal.getItem().getName(), "Date", "Price", "Time(Date)", dates, prices);

//        chart.setXAxisLabelOverrideMap(xMarkMap);
        // Save it
        BitmapEncoder.saveBitmap(chart, "charts/" + deal.getItem().getId(), BitmapEncoder.BitmapFormat.PNG);

        // or save it in high-res
        BitmapEncoder.saveBitmapWithDPI(chart, "charts/" + deal.getItem().getId() + "_dpi", BitmapEncoder.BitmapFormat.PNG, 300);
    }
}
