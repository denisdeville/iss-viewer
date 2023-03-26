package org.acme.services;

import java.io.File;

import org.acme.dto.IssCoordinates;
import org.hipparchus.util.FastMath;
import org.orekit.bodies.BodyShape;
import org.orekit.bodies.GeodeticPoint;
import org.orekit.bodies.OneAxisEllipsoid;
import org.orekit.data.DataContext;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.propagation.analytical.tle.TLE;
import org.orekit.propagation.analytical.tle.TLEPropagator;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;
import org.orekit.utils.Constants;
import org.orekit.utils.IERSConventions;
import org.orekit.utils.PVCoordinates;

import java.time.LocalDateTime;

public class TLEService {

    public static TLEService instance;

    public static TLEService getInstance() {
        if (instance == null) {
            instance = new TLEService();
        }
        return instance;
    }

    private TLE tle;
    private Frame earthFrame;
    private TLEPropagator propagator;
    private BodyShape earth;
    private AbsoluteDate absoluteDate;

    // Should find a better solution to initialize TLEService
    private String defaultLine1 = "1 25544U 98067A   23082.12970756  .00016190  00000+0  29707-3 0  9996";
    private String defaultLine2 = "2 25544  51.6419  34.8951 0005978 121.0982 331.7746 15.49406253388447";

    private TLEService() {
        File orekitData = new File("orekit-data");
        DataProvidersManager manager = DataContext.getDefault().getDataProvidersManager();
        manager.addProvider(new DirectoryCrawler(orekitData));

        tle = new TLE(defaultLine1, defaultLine2);
       
        earthFrame = FramesFactory.getITRF(IERSConventions.IERS_2010, true);
        earth = new OneAxisEllipsoid(Constants.WGS84_EARTH_EQUATORIAL_RADIUS,
                Constants.WGS84_EARTH_FLATTENING,
                earthFrame);
        propagator = TLEPropagator.selectExtrapolator(tle);
    }

    public void updateTleData(String line1, String line2) {
        tle = new TLE(line1, line2);
        propagator = TLEPropagator.selectExtrapolator(tle);
    }

    public IssCoordinates getLatitudeLongitude(LocalDateTime dateTime) {
        absoluteDate = new AbsoluteDate(
                dateTime.getYear(),
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getHour(),
                dateTime.getMinute(),
                dateTime.getSecond(),
                TimeScalesFactory.getUTC());

        PVCoordinates pvCoordinates = propagator.propagate(absoluteDate).getPVCoordinates(earthFrame);
        GeodeticPoint geodeticPoint = earth.transform(
                pvCoordinates.getPosition(),
                earthFrame,
                absoluteDate);
        double currentLatitude = FastMath.toDegrees(geodeticPoint.getLatitude());
        double currentLongitude = FastMath.toDegrees(geodeticPoint.getLongitude());

        return new IssCoordinates(currentLongitude, currentLatitude);
    }


}
