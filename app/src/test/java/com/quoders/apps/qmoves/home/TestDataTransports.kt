package com.quoders.apps.qmoves.home

import com.quoders.apps.qmoves.data.Transport

/**
 * Mocked data to use in tests involving Transports lists
 */
class TestDataTransports {

    companion object {
        val validTransportList = listOf(
            Transport("BusX",Transport.TransportType.BUS, "#123456",
                "path/meta","path/data","http://feedx"),
            Transport("MetroY",Transport.TransportType.SUBWAY, "#654321",
                "pathy/meta","pathy/data","http://feedy"),
            Transport("TramZ",Transport.TransportType.TRAM, "#001122",
                "pathz/meta","pathz/data","http://feedz")
        )

        val defaultTestTransport = Transport(
                name = "SubwayXYZ",
                type = Transport.TransportType.SUBWAY,
                color = "",
                dataPath = "",
                metadataPath = "",
                newsFeed = ""
        )
    }
}