package com.ttmv.datacenter.gromit.server.flow;

import com.ttmv.datacenter.gromit.gromitService.GromitService;
import com.ttmv.datacenter.gromit.gromitService.RMSParser;
import com.ttmv.datacenter.message.tmcp.TmcpMessage;
import com.ttmv.datacenter.utils.check.CheckParameterUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dispatcher
{
  private static Logger logger = LogManager.getLogger(Dispatcher.class);
  private ServerContainer serverContainer;

  public Dispatcher(ServerContainer serverContainer)
  {
    this.serverContainer = serverContainer;
  }

  public void handleRequestTMCP(GromitRequest request, GromitResponse response)
    throws Exception
  {
    if ((request == null) || (response == null)) {
      logger.error("传入的参数有为空. [request]=" + request + " [response]=" + response);
      throw new Exception("Failed to init [request]=" + request + " [responser]=" + response);
    }
    RMSParser parser = this.serverContainer.getRmsParser();
    if (parser == null) {
      logger.fatal("没有找到 RMSParser 接口的实现类，请检查配置文件是否正确.");
      throw new Exception("Not find RMSParser implement.");
    }
    String serviceName = parser.getServiceName(request.getMessage());
    if (serviceName == null) {
      logger.error("获得接口服务名异常，请检查传入的数据串和配置文件. [传入数据串] --->" + request.getMessage().getData());
      throw new Exception("Not find serviceName.");
    }
    logger.info("[访问服务] ---> " + serviceName);

    GromitService service = this.serverContainer.getService(serviceName);
    if (service == null) {
      logger.error("通过配置文件查找不到服务名为[" + serviceName + "]对应的实现，请检查配置文件是否正确.");
      throw new Exception("Not find service implement.");
    }
    service.service(request, response);
  }

  public void handleRequestHTTP(GromitRequest request, GromitResponse response, String uri)
    throws Exception
  {
    if ((request == null) || (response == null) || (CheckParameterUtil.checkIsEmpty(uri))) {
      logger.error("传入的参数有为空. [request]=" + request + " [responser]=" + response + " [uri]=" + uri);
      throw new Exception("Failed to init [request]=" + request + " [responser]=" + response + " [uri]=" + uri);
    }
    RMSParser parser = this.serverContainer.getRmsParser();
    if (parser == null) {
      logger.fatal("没有找到 RMSParser 的实现类，请检查配置文件是否正确.");
      throw new Exception("Not find RMSParser implement.");
    }
    String serviceName = null;
    if (uri.split("/").length == 3) {
      serviceName = parser.getServiceName(uri.split("/")[2]);
    } else {
      logger.error("传入的uri格式不对.请按照[ /系统名／服务名.do ]的格式传入。传入的uri为" + uri);
      throw new Exception("Failed to uri. [uri]=" + uri);
    }
    logger.info("[访问服务] ---> " + serviceName);
    GromitService service = this.serverContainer.getService(serviceName);
    if (service == null) {
      logger.error("找不到对应的接口实现，请检查传入的参数和配置文件. [servicName]=" + serviceName);
      throw new Exception("Not find service implement.");
    }
    service.service(request, response);
  }

  public ServerContainer getServerContainer() {
    return this.serverContainer;
  }
}