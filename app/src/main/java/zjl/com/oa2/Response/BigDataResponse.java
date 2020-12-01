package zjl.com.oa2.Response;

import java.util.List;

import zjl.com.oa2.Base.BaseResponse;

public class BigDataResponse extends BaseResponse {
    private Result Result;
    public Result getResult(){
        return this.Result;
    }

    public class Result {
        private List<Detections>  big_data ;

        public List<Detections> getBig_data() {
            return big_data;
        }
//
//        class BigData{
//            private Details[] details;
//            private String name;
//            private String description;
//            private String conclusion;
//            private String analysis;
//
//            public Details[] getDetails() {
//                return details;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public String getDescription() {
//                return description;
//            }
//
//            public String getConclusion() {
//                return conclusion;
//            }
//
//            public String getAnalysis() {
//                return analysis;
//            }
//
//            class Details{
//                private String name;
//                private String[] header;
//                private List<String[]> values;
//
//                public String getName() {
//                    return name;
//                }
//
//                public String[] getHeader() {
//                    return header;
//                }
//
//                public List<String[]> getValues() {
//                    return values;
//                }
//            }
//        }

        public class Detections
        {
            public String  name;
            public String description;
            public String conclusion;
            public String analysis;
            public List<Detail> details;

            public String getName() {
                return name;
            }

            public String getDescription() {
                return description;
            }

            public String getConclusion() {
                return conclusion;
            }

            public String getAnalysis() {
                return analysis;
            }

            public List<Detail> getDetails() {
                return details;
            }

            public class Detail
            {
                public String name;
                public List<String> header;
                public List<List<String>> values;

                public String getName() {
                    return name;
                }

                public List<String> getHeader() {
                    return header;
                }

                public List<List<String>> getValues() {
                    return values;
                }
            }
        }

    }
}
