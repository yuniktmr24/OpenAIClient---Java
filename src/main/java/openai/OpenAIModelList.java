package openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 *
 * @author ytamrakar
 * 
 * Container for dynamic list of OpenAI models response fetched from the /models endpoint
 */

public class OpenAIModelList implements Serializable {
    private static final long serialVersionUID = 1L;
	
	public static class Model implements Serializable {
        private static final long serialVersionUID = 1L;       
		
		@JsonProperty("id")
        private String id;

        @JsonProperty("object")
        private String object;

        @JsonProperty("created")
        private Long created;

        @JsonProperty("owned_by")
        private String ownedBy;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public long getCreated() {
            return created;
        }

        public void setCreated(long created) {
            this.created = created;
        }

        public String getOwnedBy() {
            return ownedBy;
        }

        public void setOwnedBy(String ownedBy) {
            this.ownedBy = ownedBy;
        }
		
		public String getFormattedCreatedDate() {
			if (this.created == null) {
				return "";
			}
            Date date = new Date(this.created * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.format(date);
        }
    }

    public static class ModelResponse implements Serializable {
        private static final long serialVersionUID = 1L;
		
        @JsonProperty("object")
        private String object;

        @JsonProperty("data")
        private List<Model> data = new ArrayList<>();

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public List<Model> getData() {
            return data;
        }

        public void setData(List<Model> data) {
            this.data = data;
        }
		
		public List<Model> getDataSortedByCreatedDate() {
			if (data != null) {
				return data.stream()
						.sorted(Comparator.comparingLong(Model::getCreated).reversed())
						.collect(Collectors.toList());
			}
			return data;
        }
    }
}

