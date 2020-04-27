package martin.dev.pricer.discord;

import martin.dev.pricer.data.model.Deal;
import martin.dev.pricer.data.model.Price;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class BotSendMessage {
    private final JDA api;
    private final long channelId = 559492315371274272L;

    public BotSendMessage(JDA api) {
        this.api = api;
    }

    public void sendMessage(String content) {
        TextChannel channel = api.getTextChannelById(this.channelId);
        if (channel != null) {
            channel.sendMessage(content).queue();
        }
    }

    public void sendEmbedded(Deal deal) {
        TextChannel channel = api.getTextChannelById(this.channelId);

        if (channel != null) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(deal.getItem().getTitle(), "http://51.83.87.167:8082/item/" + deal.getItem().getId())
                    .setThumbnail(deal.getStore().getLogo())
                    .setImage(deal.getItem().getImg())
                    .addField("Price", "" + deal.getItem().getLastPrice(), true)
                    .addField("Delta", "" + deal.getItem().getLastDelta() + "%", true)
                    .addField("Average Price", "" + deal.getItem().getAvgPrice(), true)
                    .addField("Max Price", "" + deal.getItem().getMaxPrice(), true)
                    .addField("Min Price", "" + deal.getItem().getMinPrice(), true);

//            if (deal.getItem().getImg() != null && !deal.getItem().getImg().isEmpty()) {
//                embedBuilder.setImage(deal.getItem().getImg());
//            }
//            channel.sendMessage(embedBuilder.build()).queue();
        }

    }

    public void buildChart(Deal deal) throws IOException {

        double[] dates = IntStream.range(1, deal.getItem().getPrices().size() + 1).mapToDouble(value -> value + 0.0).toArray();

        double[] prices = deal.getItem().getPrices().stream().mapToDouble(Price::getPrice).toArray();

        // Create Chart
        XYChart chart = QuickChart.getChart(deal.getItem().getTitle(), "Date", "Price", "Time(Date)", dates, prices);

//        chart.setXAxisLabelOverrideMap(xMarkMap);
        // Save it
        BitmapEncoder.saveBitmap(chart, "charts/" + deal.getItem().getId(), BitmapEncoder.BitmapFormat.PNG);

        // or save it in high-res
        BitmapEncoder.saveBitmapWithDPI(chart, "charts/" + deal.getItem().getId() + "_dpi", BitmapEncoder.BitmapFormat.PNG, 300);
    }
}
