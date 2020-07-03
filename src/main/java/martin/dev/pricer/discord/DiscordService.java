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

    public DiscordService(JDA api) {
        this.api = api;
    }

    public void sendMessage(Deal deal, long channelId) {
        if (deal.getItem().getImg() != null && !deal.getItem().getImg().equals("")){
            this.sendEmbeddedWithImage(deal, channelId);
        } else {
            this.sendEmbeddedWithoutImage(deal, channelId);
        }
    }

    private void sendEmbeddedWithImage(Deal deal, long channelId) {
        TextChannel channel = api.getTextChannelById(channelId);

        if (channel != null) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(deal.getItem().getName(), "http://51.83.87.167:8080/deal/" + deal.getId())
//            embedBuilder.setTitle(deal.getItem().getName(), deal.getItem().getUrl())
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
                if (deal.getItem().getImg().startsWith("//")) {
                    embedBuilder.setImage("http://" + deal.getItem().getImg().replace("//", ""));
                }
            }

            channel.sendMessage(embedBuilder.build()).queue();
        }
    }

    private void sendEmbeddedWithoutImage(Deal deal, long channelId) {
        TextChannel channel = api.getTextChannelById(channelId);

        if (channel != null) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(deal.getItem().getName(), "http://51.83.87.167:8080/deal/" + deal.getId())
//            embedBuilder.setTitle(deal.getItem().getName(), deal.getItem().getUrl())
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
